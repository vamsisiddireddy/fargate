resource "aws_ecs_cluster" "ecs" {
  name = var.cluster_name
}

resource "aws_ecs_service" "service1" {
  name                   = var.service1_name
  cluster                = aws_ecs_cluster.ecs.arn
  launch_type            = "FARGATE"
  enable_execute_command = true

  deployment_maximum_percent         = 200
  deployment_minimum_healthy_percent = 100
  desired_count                      = 1
  task_definition                    = aws_ecs_task_definition.td1.arn

  network_configuration {
    assign_public_ip = true
    subnets          = tolist(data.aws_subnet.my_subnets[*].id)
  }

  load_balancer {
    target_group_arn = data.aws_lb_target_group.example1.arn
    container_name   = var.container1_name
    container_port   = 3000
  }
}

resource "aws_ecs_task_definition" "td1" {
  container_definitions = jsonencode([
    {
      name      = var.container1_name
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
  family                   = var.family1_name
  requires_compatibilities = ["FARGATE"]
  cpu                      = "2048"
  memory                   = "4096"
  network_mode             = "awsvpc"
  task_role_arn            = "arn:aws:iam::416007387859:role/ecsTaskExecutionRole"
  execution_role_arn       = "arn:aws:iam::416007387859:role/ecsTaskExecutionRole"
}

resource "aws_ecs_service" "service2" {
  name                   = var.service2_name
  cluster                = aws_ecs_cluster.ecs.arn
  launch_type            = "FARGATE"
  enable_execute_command = true

  deployment_maximum_percent         = 200
  deployment_minimum_healthy_percent = 100
  desired_count                      = 1
  task_definition                    = aws_ecs_task_definition.td2.arn

  network_configuration {
    assign_public_ip = true
    subnets          = tolist(data.aws_subnet.my_subnets[*].id)
  }

  load_balancer {
    target_group_arn = data.aws_lb_target_group.example2.arn
    container_name   = var.container2_name
    container_port   = 8080
  }
}

resource "aws_ecs_task_definition" "td2" {
  container_definitions = jsonencode([
    {
      name      = var.container2_name
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
  family                   = var.family2_name
  requires_compatibilities = ["FARGATE"]
  cpu                      = "2048"
  memory                   = "4096"
  network_mode             = "awsvpc"
  task_role_arn            = "arn:aws:iam::416007387859:role/ecsTaskExecutionRole"
  execution_role_arn       = "arn:aws:iam::416007387859:role/ecsTaskExecutionRole"
}
