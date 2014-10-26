Server URL: http://104.131.184.188:8080/restoserver/
=================

- http://104.131.184.188:8080/restoserver/signIn

  **POST: login=...&hash=...**

  Return JSON { "token" = "...(some correct token)"} or {"error" = "access denied"} or {"error" = "login not exists"}


- http://104.131.184.188:8080/restoserver/signUp

  **POST: login=...&hash=...&name=...**
  
  Retun JSON {"success" = "true"} or {"error" = "login already exists"}
  

- http://104.131.184.188:8080/restoserver/getUserProfile?token=...
  
  Return JSON for user with this token or {"error" = "access denied"}


- http://104.131.184.188:8080/restoserver/getWaiters

  Return list of all waiters


- http://104.131.184.188:8080/restoserver/vote

  **POST: token=...&waiterId=...&review=...&rating=...**

  Return JSON {'success' = 'true'} or {"error" = "access denied"}
