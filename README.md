# database-steps

[![Build Status](https://ci.jenkins.io/job/Plugins/job/database-steps-plugin/job/master/badge/icon)](https://ci.jenkins.io/job/Plugins/job/database-steps-plugin/job/master/)
[![Contributors](https://img.shields.io/github/contributors/jenkinsci/database-steps-plugin.svg)](https://github.com/jenkinsci/database-steps-plugin/graphs/contributors)
[![Jenkins Plugin](https://img.shields.io/jenkins/plugin/v/database-steps.svg)](https://plugins.jenkins.io/database-steps)
[![GitHub release](https://img.shields.io/github/release/jenkinsci/database-steps-plugin.svg?label=changelog)](https://github.com/jenkinsci/database-steps-plugin/releases/latest)
[![Jenkins Plugin Installs](https://img.shields.io/jenkins/plugin/i/database-steps.svg?color=blue)](https://plugins.jenkins.io/database-steps)

## Introduction

This plugin provide a connection for databases(ORACLE, MYSQL and POSTGRES)

To connect in one of this databases:


```
node {
def result = databaseConnect(
        [
        	dbTyp: 'POSTGRES', -> POSTGRES/ORACLE/MYSQL
            database: 'dbname', 
            host: 'hostname', 
            user: 'username', 
            password: 'password', 
            port: 'port', 
            query: "select teste ..."
        ]
    )

    echo result.query[0].teste
}
```

This plugin allow recovery a json result.

## Getting started

TODO Tell users how to configure your plugin here, include screenshots, pipeline examples and 
configuration-as-code examples.

## Issues

TODO Decide where you're going to host your issues, the default is Jenkins JIRA, but you can also enable GitHub issues,
If you use GitHub issues there's no need for this section; else add the following line:

Report issues and enhancements in the [Jenkins issue tracker](https://issues.jenkins-ci.org/).

## Contributing

TODO review the default [CONTRIBUTING](https://github.com/jenkinsci/.github/blob/master/CONTRIBUTING.md) file and make sure it is appropriate for your plugin, if not then add your own one adapted from the base file

Refer to our [contribution guidelines](https://github.com/jenkinsci/.github/blob/master/CONTRIBUTING.md)

## LICENSE

Licensed under MIT, see [LICENSE](LICENSE.md)

