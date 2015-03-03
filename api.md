Server URL: http://104.131.184.188:8080/restoserver/
=================

|     Command   | Method |Parameters| Response |
|:-------------:|:------:|--------:|:-------:|
| signIn        |  POST  |login, hash      | { "token" = "...(some correct token)"} or {"error" = "access denied" or "login not exists"} |
|      signUp   |  POST  |login, hash, name| {"success" = "true"} or {"error" = "login already exists"}   |
| getUserProfile|  GET   |    token        |JSON for user with this token or {"error" = "access denied" or "token is expired"} |
| vote          |  POST  |token, waiterId, review, rating| {"success" = "true"} or {"error" = "access denied"} |
| getWaiters    |  GET   |   -           |List of all waiters |
| getWaiter     |  GET   |token, waiterId| JSON for waiter with this ID or {"error" = "access denied"} |
| download      |  GET   |token          | picture of user with this token or 500(Internal server error) or 403(forbidden) |
| upload        | POST   |token, data    | {"success" = "true"} or {"error" = "access denied" or "token is expired"} |
