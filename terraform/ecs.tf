resource "aws_ecs_cluster" "ecs" {
  name = "app_cluster"
}

resource "aws_ecs_service" "service1" {
  name                   = "app_service1"
  cluster                = aws_ecs_cluster.ecs.arn
  launch_type            = "FARGATE"
  enable_execute_command = true

  deployment_maximum_percent         = 200
  deployment_minimum_healthy_percent = 100
  desired_count                      = 1
  task_definition                    = aws_ecs_task_definition.td1.arn

  network_configuration {
    assign_public_ip = true
    security_groups  = [aws_security_group.sg.id]
    subnets          = [aws_subnet.sn1.id, aws_subnet.sn2.id, aws_subnet.sn3.id]
  }
  load_balancer {
    target_group_arn = aws_lb_target_group.ip-1.arn
    container_name   = "App1"
    container_port   = 3000
  }

  #depends_on = [aws_alb_listener.front_end, aws_iam_role_policy_attachment.ecs_task_execution_role]

}

resource "aws_ecs_task_definition" "td1" {
  container_definitions = jsonencode([
    {
      name      = "App1"
      image     = "satulakhil/frontend:React"
      cpu       = 2048
      memory    = 4096
      essential = true
      portMappings = [
        {
          containerPort = 3000
          hostPort      = 3000
        }
      ]
    }
  ])
  family                   = "App1"
  requires_compatibilities = ["FARGATE"]

  cpu                = "2048"
  memory             = "4096"
  network_mode       = "awsvpc"
  task_role_arn      = "arn:aws:iam::232253909027:role/ecsTaskExecutionRole"
  execution_role_arn = "arn:aws:iam::232253909027:role/ecsTaskExecutionRole"
}
resource "aws_ecs_service" "service2" {
  name                   = "app_service2"
  cluster                = aws_ecs_cluster.ecs.arn
  launch_type            = "FARGATE"
  enable_execute_command = true

  deployment_maximum_percent         = 200
  deployment_minimum_healthy_percent = 100
  desired_count                      = 1
  task_definition                    = aws_ecs_task_definition.td2.arn

  network_configuration {
    assign_public_ip = true
    security_groups  = [aws_security_group.sg.id]
    subnets          = [aws_subnet.sn1.id, aws_subnet.sn2.id, aws_subnet.sn3.id]
  }
  load_balancer {
    target_group_arn = aws_lb_target_group.ip-2.arn
    container_name   = "App2"
    container_port   = 8080
  }
}

resource "aws_ecs_task_definition" "td2" {
  container_definitions = jsonencode([
    {
      name      = "App2"
      image     = "satulakhil/backend:java"
      cpu       = 2048
      memory    = 4096
      essential = true
      portMappings = [
        {
          containerPort = 8080
          hostPort      = 8080
        }
      ]
    }
  ])
  family                   = "App2"
  requires_compatibilities = ["FARGATE"]

  cpu                = "2048"
  memory             = "4096"
  network_mode       = "awsvpc"
  task_role_arn      = "arn:aws:iam::232253909027:role/ecsTaskExecutionRole"
  execution_role_arn = "arn:aws:iam::232253909027:role/ecsTaskExecutionRole"
}