resource "aws_lb" "test" {
  name               = "test-lb-tf"
  internal           = false
  load_balancer_type = "application"
  subnets            = [aws_subnet.sn1.id, aws_subnet.sn2.id, aws_subnet.sn1.id]
}
resource "aws_lb_target_group" "ip-1" {
  name        = "tg1"
  port        = 80
  protocol    = "HTTP"
  target_type = "ip"
  vpc_id      = aws_vpc.vpc.id
}
resource "aws_lb_target_group" "ip-2" {
  name        = "tg2"
  port        = 80
  protocol    = "HTTP"
  target_type = "ip"
  vpc_id      = aws_vpc.vpc.id
}
resource "aws_lb_listener" "front_end" {
  load_balancer_arn = aws_lb.test.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.ip-1.arn
  }
}
resource "aws_lb_listener_rule" "path" {
  listener_arn = aws_lb_listener.front_end.arn
  priority     = 100

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.ip-2.arn
  }

  condition {
    path_pattern {
      values = ["/adminlogin*", "/patientlogin*", "/doctorlogin*"]
    }
  }
}