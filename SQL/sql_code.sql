--建立place table
CREATE TABLE PLACE(VILLAGE_ID VARCHAR2(20)primary key, 
	PLACE_ID VARCHAR2(20), 
	ADDRESS NVARCHAR2(50), 
	TYPE_ID VARCHAR2(20), 
	POLICE_ID VARCHAR2(20))
    
--新增data到place table
insert into place values('C001','P001', '苗栗縣竹南鎮中埔街20號', 'T001', 'M001');
insert into place values('C002', 'P002','苗栗縣竹南鎮和平街79號',	'T002',	'M001');
insert into place values('C003'	,'P003','苗栗縣竹南鎮龍山路三段142號',	'T002',	'M001');
insert into place values('C004','P004',	'苗栗縣後龍鎮中華路1498號','T003',	'M001');
insert into place values('C005','P005',	'苗栗縣苗栗市米市街80號',	'T001',	'M002');
insert into place values('C005','P006',	'苗栗縣苗栗市光復路117號',	'T001','M002');
insert into place values('C005'	,'P007','苗栗縣苗栗市博愛街109號',	'T002',	'M002');
insert into place values('C005'	,'P008','苗栗縣苗栗市大同路53號',	'T002',	'M002');
insert into place values('C006','P009','苗栗縣頭份市民族里和平路102號','T003','M003');
insert into place values('C007','P010',	'苗栗縣頭份市忠孝忠孝一路69號','T004','M003');
insert into place values('C008','P011',	'苗栗縣頭份市信義里中正路65號','T001','M003');
insert into place values('C008','P012',	'苗栗縣頭份市信義里中正路116號','T004',	'M003');

--建立police table
CREATE TABLE POLICE("POLICE_ID VARCHAR2(20)primary key, 
	POLICE_NAME NVARCHAR2(50), 
	ADDRESS NVARCHAR2(50), 
	PHONE VARCHAR2(20))

--新增data到police table
insert into police values('M001','竹南分局','苗栗縣竹南鎮民族街72號','03-7474796');
insert into police values('M002','苗栗分局	','苗栗縣苗栗市金鳳街109號','03-7320059');
insert into police values('M003','頭份分局','苗栗縣頭份市中興路503號','03-7663004');

--建立village table
create table village(village_id varchar2(20)primary key,village_name nvarchar2(50), address nvarchar2(50), phone varchar2(20))

--新增data到village table
insert into village values('C001',	'大埔里','竹南鎮公義路1035號',	'037-581072');
insert into village values('C002',	'竹南里', '竹南鎮竹南里中山路103號', '037-472735');
insert into village values('C003',	'山佳里', '竹南鎮山佳里國光街14號',	'037-614186');
insert into village values('C004',	'埔頂里', '後龍鎮埔頂里中興路136-1號','037-724839');
insert into village values('C005',	'綠苗里', '苗栗市綠苗里中正路766號','037-333240');
insert into village values('C006',	'民族里', '民族里民族路96號',	'037-660001');
insert into village values('C007',	'忠孝里', '忠孝里光大街82號',	'037-661145');
insert into village values('C008',	'信義里', '信義里信義路53巷1號'	,'037-616072');

--建立facility table
create table facility(facility_id varchar2(20)primary key, address nvarchar2(50),type nvarchar2(20),people_num number(10),basement_layer number(10))

--新增data到facility table
insert into facility values('P001', '苗栗縣竹南鎮中埔街20號', '公寓',100, 1);
insert into facility values('P002',	'苗栗縣竹南鎮和平街79號', '大樓',3142,	1);
insert into facility values('P003',	'苗栗縣竹南鎮龍山路三段142號', '大樓',1072,	1);
insert into facility values('P004',	'苗栗縣後龍鎮中華路1498號', '公共設施',32,	1);
insert into facility values('P005',	'苗栗縣苗栗市米市街80號', '公寓',106,	1);
insert into facility values('P006',	'苗栗縣苗栗市光復路117號',	'公寓',26,	1);
insert into facility values('P007',	'苗栗縣苗栗市博愛街109號',	'大樓',2038,	2);
insert into facility values('P008',	'苗栗縣苗栗市大同路53號',	'大樓',128,	2);
insert into facility values('P009',	'苗栗縣頭份市民族里和平路102號', '公共設施', 353, 1);
insert into facility values('P010',	'苗栗縣頭份市忠孝忠孝一路69號',	'私營單位', 501,	1);
insert into facility values('P011',	'苗栗縣頭份市信義里中正路65號',	'公寓', 194,	1);
insert into facility values('P012',	'苗栗縣頭份市信義里中正路116號', '私營單位',	78,	1);

--4-1. 列出轄管區域內有單一避難設施大於 1000 容人數量的轄管分局及分局連絡電話。
select  distinct pol.police_name as 轄管分局, pol.phone as 分局連絡電話
 from place
 left join facility fac on fac.facility_id = place.place_id
 left join police pol on pol.police_id = place.police_id
  where fac.people_num > 1000;
  
--4-2. 列出轄管區域內有單一避難設施大於 1000 容人數量的轄管分局及分局連絡電話,並計算出設施數量。(關鍵字:partition)
--法一
select  pol.police_name as 轄管分局, pol.phone as 分局連絡電話, count(pol.police_name) as 設施數量
 from place
 left join facility fac on fac.facility_id = place.place_id
 left join police pol on pol.police_id = place.police_id
  where fac.people_num > 1000
   group by pol.police_name, pol.phone;
   
--法二  
select  distinct pol.police_name as 轄管分局, pol.phone as 分局連絡電話, count(fac.address)over (partition by pol.police_name) as 設施數量
 from place
 left join facility fac on fac.facility_id = place.place_id
 left join police pol on pol.police_id = place.police_id
  where fac.people_num > 1000
   
   
--4-3. 承上題,再補上避難設施地址、類型。
select  pol.police_name as 轄管分局, pol.phone as 分局連絡電話, count(fac.address)over (partition by pol.police_name) as 設施數量,
       fac.address as 避難設施地址, fac.type as 類型
 from place
 left join facility fac on fac.facility_id = place.place_id
 left join police pol on pol.police_id = place.police_id
  where fac.people_num > 1000
   
--4-4. 查詢設施地址包含「中」字的避難設施,列出資料必須含村里別、避難設施地址、容人數量、轄管分局及分局連絡電話。
select vil.village_name as 村里別, place.address as 避難設施地址, 
       fac.people_num as 容人數量, pol.police_name as 轄管分局, pol.phone as 分局連絡電話
 from place
  left join village vil on vil.village_id = place.village_id
  left join facility fac on fac.facility_id = place.place_id
  left join police pol on pol.police_id = place.police_id
 where place.address like '%中%';
 
-- 4-5. 查詢所有類別為公寓及大樓的避難設施,列出資料必須包含村里別、村里辦公室位置、避難設施地址、容人數量。
select fac.address as 避難設施地址, vil.village_name as 村里別, vil.address as 村里辦公室位置, fac.people_num as 容人數量
 from facility fac
  left join place on place.place_id = fac.facility_id
  left join village vil on vil.village_id = place.village_id
   where type in ('公寓', '大樓');

--5-1. 更新避難設施地址是「苗栗縣竹南鎮和平街 79 號」的容人數量為 5000 人。
update facility
 set people_num = 5000
  where address = '苗栗縣竹南鎮和平街79號';
 commit;
  
--5-2. 刪除避難設施小於 1000 容人數量的資料。
delete from place
 where place_id in (
                     select facility_id
                     from facility
                     where people_num < 1000);
delete from facility where people_num < 1000;
commit;