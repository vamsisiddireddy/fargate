data "aws_vpc" "my_vpc" {
  id = "vpc-03764d332c81fdb3d" # Replace with your VPC ID
}

data "aws_subnet" "my_subnets" {
  count = 6 # Replace with the number of subnets you want to fetch

  vpc_id     = data.aws_vpc.my_vpc.id
  cidr_block = element(["172.31.32.0/20", "172.31.0.0/20", "172.31.16.0/20", "172.31.48.0/20", "172.31.64.0/20", "172.31.80.0/20"], count.index)
}

output "subnet_ids" {
  value = data.aws_subnet.my_subnets[*].id
}
