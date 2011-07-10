update "letter" set letter_order=letter_order-3 where id>230 and id <237;
update "letter" set letter_order=letter_order-3 where id>180 and id <187;
delete from "letter" where id>=178 and id<=180;
delete from "letter" where id>=228 and id<=230;