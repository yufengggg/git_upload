--�إ�place table
CREATE TABLE PLACE(VILLAGE_ID VARCHAR2(20)primary key, 
	PLACE_ID VARCHAR2(20), 
	ADDRESS NVARCHAR2(50), 
	TYPE_ID VARCHAR2(20), 
	POLICE_ID VARCHAR2(20))
    
--�s�Wdata��place table
insert into place values('C001','P001', '�]�߿��˫n���H��20��', 'T001', 'M001');
insert into place values('C002', 'P002','�]�߿��˫n��M����79��',	'T002',	'M001');
insert into place values('C003'	,'P003','�]�߿��˫n���s�s���T�q142��',	'T002',	'M001');
insert into place values('C004','P004',	'�]�߿����s���ظ�1498��','T003',	'M001');
insert into place values('C005','P005',	'�]�߿��]�ߥ��̥���80��',	'T001',	'M002');
insert into place values('C005','P006',	'�]�߿��]�ߥ����_��117��',	'T001','M002');
insert into place values('C005'	,'P007','�]�߿��]�ߥ��շR��109��',	'T002',	'M002');
insert into place values('C005'	,'P008','�]�߿��]�ߥ��j�P��53��',	'T002',	'M002');
insert into place values('C006','P009','�]�߿��Y�������ڨ��M����102��','T003','M003');
insert into place values('C007','P010',	'�]�߿��Y�������������@��69��','T004','M003');
insert into place values('C008','P011',	'�]�߿��Y�����H�q��������65��','T001','M003');
insert into place values('C008','P012',	'�]�߿��Y�����H�q��������116��','T004',	'M003');

--�إ�police table
CREATE TABLE POLICE("POLICE_ID VARCHAR2(20)primary key, 
	POLICE_NAME NVARCHAR2(50), 
	ADDRESS NVARCHAR2(50), 
	PHONE VARCHAR2(20))

--�s�Wdata��police table
insert into police values('M001','�˫n����','�]�߿��˫n����ڵ�72��','03-7474796');
insert into police values('M002','�]�ߤ���	','�]�߿��]�ߥ������109��','03-7320059');
insert into police values('M003','�Y������','�]�߿��Y����������503��','03-7663004');

--�إ�village table
create table village(village_id varchar2(20)primary key,village_name nvarchar2(50), address nvarchar2(50), phone varchar2(20))

--�s�Wdata��village table
insert into village values('C001',	'�j�H��','�˫n���q��1035��',	'037-581072');
insert into village values('C002',	'�˫n��', '�˫n��˫n�����s��103��', '037-472735');
insert into village values('C003',	'�s�Ψ�', '�˫n��s�Ψ������14��',	'037-614186');
insert into village values('C004',	'�H����', '���s��H����������136-1��','037-724839');
insert into village values('C005',	'��]��', '�]�ߥ���]��������766��','037-333240');
insert into village values('C006',	'���ڨ�', '���ڨ����ڸ�96��',	'037-660001');
insert into village values('C007',	'������', '���������j��82��',	'037-661145');
insert into village values('C008',	'�H�q��', '�H�q���H�q��53��1��'	,'037-616072');

--�إ�facility table
create table facility(facility_id varchar2(20)primary key, address nvarchar2(50),type nvarchar2(20),people_num number(10),basement_layer number(10))

--�s�Wdata��facility table
insert into facility values('P001', '�]�߿��˫n���H��20��', '���J',100, 1);
insert into facility values('P002',	'�]�߿��˫n��M����79��', '�j��',3142,	1);
insert into facility values('P003',	'�]�߿��˫n���s�s���T�q142��', '�j��',1072,	1);
insert into facility values('P004',	'�]�߿����s���ظ�1498��', '���@�]�I',32,	1);
insert into facility values('P005',	'�]�߿��]�ߥ��̥���80��', '���J',106,	1);
insert into facility values('P006',	'�]�߿��]�ߥ����_��117��',	'���J',26,	1);
insert into facility values('P007',	'�]�߿��]�ߥ��շR��109��',	'�j��',2038,	2);
insert into facility values('P008',	'�]�߿��]�ߥ��j�P��53��',	'�j��',128,	2);
insert into facility values('P009',	'�]�߿��Y�������ڨ��M����102��', '���@�]�I', 353, 1);
insert into facility values('P010',	'�]�߿��Y�������������@��69��',	'�p����', 501,	1);
insert into facility values('P011',	'�]�߿��Y�����H�q��������65��',	'���J', 194,	1);
insert into facility values('P012',	'�]�߿��Y�����H�q��������116��', '�p����',	78,	1);

--4-1. �C�X�Һްϰ줺����@�����]�I�j�� 1000 �e�H�ƶq���Һޤ����Τ����s���q�ܡC
select  distinct pol.police_name as �Һޤ���, pol.phone as �����s���q��
 from place
 left join facility fac on fac.facility_id = place.place_id
 left join police pol on pol.police_id = place.police_id
  where fac.people_num > 1000;
  
--4-2. �C�X�Һްϰ줺����@�����]�I�j�� 1000 �e�H�ƶq���Һޤ����Τ����s���q��,�íp��X�]�I�ƶq�C(����r:partition)
--�k�@
select  pol.police_name as �Һޤ���, pol.phone as �����s���q��, count(pol.police_name) as �]�I�ƶq
 from place
 left join facility fac on fac.facility_id = place.place_id
 left join police pol on pol.police_id = place.police_id
  where fac.people_num > 1000
   group by pol.police_name, pol.phone;
   
--�k�G  
select  distinct pol.police_name as �Һޤ���, pol.phone as �����s���q��, count(fac.address)over (partition by pol.police_name) as �]�I�ƶq
 from place
 left join facility fac on fac.facility_id = place.place_id
 left join police pol on pol.police_id = place.police_id
  where fac.people_num > 1000
   
   
--4-3. �ӤW�D,�A�ɤW�����]�I�a�}�B�����C
select  pol.police_name as �Һޤ���, pol.phone as �����s���q��, count(fac.address)over (partition by pol.police_name) as �]�I�ƶq,
       fac.address as �����]�I�a�}, fac.type as ����
 from place
 left join facility fac on fac.facility_id = place.place_id
 left join police pol on pol.police_id = place.police_id
  where fac.people_num > 1000
   
--4-4. �d�߳]�I�a�}�]�t�u���v�r�������]�I,�C�X��ƥ����t�����O�B�����]�I�a�}�B�e�H�ƶq�B�Һޤ����Τ����s���q�ܡC
select vil.village_name as �����O, place.address as �����]�I�a�}, 
       fac.people_num as �e�H�ƶq, pol.police_name as �Һޤ���, pol.phone as �����s���q��
 from place
  left join village vil on vil.village_id = place.village_id
  left join facility fac on fac.facility_id = place.place_id
  left join police pol on pol.police_id = place.police_id
 where place.address like '%��%';
 
-- 4-5. �d�ߩҦ����O�����J�Τj�Ӫ������]�I,�C�X��ƥ����]�t�����O�B�����줽�Ǧ�m�B�����]�I�a�}�B�e�H�ƶq�C
select fac.address as �����]�I�a�}, vil.village_name as �����O, vil.address as �����줽�Ǧ�m, fac.people_num as �e�H�ƶq
 from facility fac
  left join place on place.place_id = fac.facility_id
  left join village vil on vil.village_id = place.village_id
   where type in ('���J', '�j��');

--5-1. ��s�����]�I�a�}�O�u�]�߿��˫n��M���� 79 ���v���e�H�ƶq�� 5000 �H�C
update facility
 set people_num = 5000
  where address = '�]�߿��˫n��M����79��';
 commit;
  
--5-2. �R�������]�I�p�� 1000 �e�H�ƶq����ơC
delete from place
 where place_id in (
                     select facility_id
                     from facility
                     where people_num < 1000);
delete from facility where people_num < 1000;
commit;