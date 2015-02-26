Server URL: http://104.131.184.188:8080/restoserver/
=================

|     Command  | Method |Parameters| Response |
|:------------:|:------:|--------:|:-------:|
| signIn       |  POST  |login, hash      | { "token" = "...(some correct token)"} or {"error" = "access denied" or "login not exists"} |
|      signUp  |  POST  |login, hash, name| {"success" = "true"} or {"error" = "login already exists"}   |
|getUserProfile|  GET   |    token        |   JSON for user with this token or {"error" = "access denied"} |
| vote         |  POST  |token, waiterId, review, rating| {"success" = "true"} or {"error" = "access denied"} |
| getWaiters   |  GET   |    - |List of all waiters |
| getWaiter    |  GET   |token, waiterId| JSON for waiter with this ID or {"error" = "access denied"} |
