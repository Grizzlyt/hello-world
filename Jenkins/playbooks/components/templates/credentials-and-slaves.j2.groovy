import com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey
import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.SystemCredentialsProvider
import org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl
import hudson.model.Node
import hudson.util.Secret
import hudson.plugins.sshslaves.SSHLauncher
import hudson.slaves.DumbSlave
import hudson.slaves.RetentionStrategy
import jenkins.model.Jenkins

def jenkins = Jenkins.instance
def credStore = jenkins.getExtensionList(SystemCredentialsProvider.class)[0].store


def privateKey = new BasicSSHUserPrivateKey(
    CredentialsScope.SYSTEM,
    "student-ssh",
    "student",
    new BasicSSHUserPrivateKey.UsersPrivateKeySource(),
    null,
    "SSH private key"
)
credStore.addCredentials(Domain.global(), privateKey)

def privateKeyDocker = new BasicSSHUserPrivateKey(
    CredentialsScope.SYSTEM,
    "jenkins-ssh",
    "jenkins",
    new BasicSSHUserPrivateKey.UsersPrivateKeySource(),
    null,
    "SSH private key"
)
credStore.addCredentials(Domain.global(), privateKeyDocker)

def slave_docker = new DumbSlave(
    "{{ jenkins_docker_slave_label }}",
    "{{ jenkins_docker_slave_name }}",
    "{{ jenkins_slave_home }}",
    "{{ jenkins_slave_num_executors }}",
    Node.Mode.NORMAL,
    "{{ jenkins_docker_slave_label }}",
    new SSHLauncher("{{ jenkins_docker_slave_name }}",  22, privateKeyDocker, null, null, null, null, null, null, null),
    new RetentionStrategy.Always(),
    new LinkedList()
)

jenkins.addNode(slave_docker)