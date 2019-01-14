provider "aws" {
    region = "us-east-2"
    access_key = "AKIAJA7772BFFKIYP4VQ"
    secret_key = "D/M426XhI3E14Foae6VzJA0FXnRPaWs+Sbaz94m+"
}


resource "aws_instance" "example" {
    ami = "ami-0170ffc1abff2ebdc"
    instance_type = "t2.micro"
    key_name = = "niru_key_pair"
    security_groups = ["default"]  
    tags{
	Name = "terraform-instance"
    }
}