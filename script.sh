#!/bin/bash

# Check if git command is available
#if ! command -v git &> /dev/null; then
#    echo "Git is not installed."
#    echo "git is installing."
#   yum install -y git
#else
#    echo "Git is installed."
#fi

# Check if Terraform command is available
if ! command -v terraform &> /dev/null; then
    echo "Terraform is not installed. Installing..."
    sudo yum install -y yum-utils shadow-utils
    sudo yum-config-manager --add-repo https://rpm.releases.hashicorp.com/AmazonLinux/hashicorp.repo
    sudo yum -y install terraform

    # Verify installation
    terraform version

    echo "Terraform installation complete."
else
    echo "Terraform is already installed."
fi
