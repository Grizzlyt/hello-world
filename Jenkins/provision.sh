#!/bin/bash

cd $(cd $(dirname $0); pwd)

mkdir /var/log/provision/

ansible-playbook playbooks/provision.yml -i jenkins.myddns.rocks, -c paramiko, -u devops -vv \
    -e @playbooks/vars.yml | 
    tee /var/log/provision/ansible-$(date +%Y%m%d-%H%S).log
