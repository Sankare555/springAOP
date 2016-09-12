We have an existing application, which is an SNMP client that is based
on SNMP4J. The application does only one simple thing: it retrieves
the sysDescr ("system description") OID from an SNMP agent, in an
endless loop, every second.

We would like to extend this application with performance monitoring
of the SNMP layer. For example some of the things we would like to
count are:

- the number of SNMP requests, preferably per request type and/or per
  SNMP version.

- the number of SNMP requests that timed out

- the number of SNMP requests that gave an error

- the number of OID's that we requested and successfully retrieved

- the amount of bytes we send/receive.

- the amount of UDP packets we send/receive.

The goal of the exercise is *not* to implement as many statistics as
possible. Instead, the goal is to implement it in a clean way, so one
or a few statistics are sufficient to demonstrate the design. Start
with the number of SNMP requests.

An important requirement is that we would like to implement this,
without having to change all the existing application code (of course
the application code is very limited in this example, but in a real
application it would be much bigger). So take that in mind for your
solution.

Another requirement is to use the Coda Hale Metrics library
(http://metrics.codahale.com) to implement the statistics collection.

Some hints:

- The whole application is a single, standalone maven project, with
  all the dependencies that you will need. You should be able to just
  load this into your favorite IDE (Eclipse or IntelliJ or ...), and
  that should download all the dependencies.

- If you're missing maven dependencies (jar) files in the AMS Maven
  repository server, then just reconfigure maven to use the NA
  repository server in Antwerp:

  http://nexus-1.be.alcatel-lucent.com:8081/nexus/content/groups/

  You can configure this in ~/.m2/settings.xml

- First start a test SNMP agent, using the Simulator class under the
  src/test/java directory.

- You can run the actual application using the Main class under the
  src/main/java directory.

- The initialization of the Coda Hale Metrics library is already
  implemented, with the simplest possible statistics reporting (print
  the statistics to standard output, every five seconds). You don't
  have to change anything to this.

