Server URL: http://104.131.184.188:8080/restoserver/
=================

- http://104.131.184.188:8080/restoserver/signIn?login=...&hash=...
  
  Return json { "token" = "...(some correct token)" or "access denied" } or {"success" = false}

- http://104.131.184.188:8080/restoserver/signUp?login=...&hash=...&name=...
  
  Retun json {"success" = true or false}
  

- http://104.131.184.188:8080/restoserver/getUserProfile?token=...
  
  Return JSON for user with this token.


- http://104.131.184.188:8080/restoserver/getWaiters

  Return list of all waiters



- http://104.131.184.188:8080/restoserver/vote?userId=...&waiterId=...&review=...&rating=...

  Return JSON {'success' = 'true' or 'false'}
