provider "aws" {
  region = "us-east-1"
}

data "aws_lb" "example" {
  name = "lb-1"
}

output "load_balancer_arn" {
  value = data.aws_lb.example.arn
}

data "aws_lb_target_group" "example1" {
  name = "tg1"
}

output "target_group_arn_1" {
  value = data.aws_lb_target_group.example1.arn
}

data "aws_lb_target_group" "example2" {
  name = "tg2"
}

output "target_group_arn_2" {
  value = data.aws_lb_target_group.example2.arn
}
