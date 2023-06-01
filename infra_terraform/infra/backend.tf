terraform {
  backend "s3" {
    bucket = "terrabucket1193"
    key    = "terraform.tfstate"
    region = "us-east-1"
  }
}