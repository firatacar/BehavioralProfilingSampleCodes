char={'t','ta','tb','tc'};
user_count={'1','2','3','4','5','6','7','8','9','10'};
user_no = {'29','26','66','88','77','58','8','14','7','35','10','47','71','28','97','84','38','72','45','91'};

%Train & test starts
for index=1:length(user_no)
    for index2=1:length(char)
        for index3=1:length(user_count)
            sD=som_read_data(strcat('activity',user_no{index},user_count{index3},char{index2},'.data')); % Train Dataset
            
            
            if char{index2}=='t'
                sD=som_normalize(sD,'log',[1 4 5]);
                sD=som_normalize(sD,'logistic',[2 3]);
            end
            if char{index2}=='ta'
                sD=som_normalize(sD,'log',[3 4]);
                sD=som_normalize(sD,'logistic',[1 2]);
            end
            if char{index2}=='tb'
                sD=som_normalize(sD,'log',[1 3 4]);
                sD=som_normalize(sD,'logistic',[2]);
            end
            if char{index2}=='tc'
                sD=som_normalize(sD,'log',[2 3]);
                sD=som_normalize(sD,'logistic',[1]);
            end
            
            
            sM=som_make(sD);
            sM=som_autolabel(sM,sD,'vote');
            som_show(sM,'empty','Train','empty','Test');
            htrain1=som_hits(sM,sD); % SD.data
            h1 = som_hits(sM,sD.data(1:475,:)); % Yes Data in Train Dataset
            h2 = som_hits(sM,sD.data(476:950,:)); % No Data in Train Dataset
            som_show_add('hit',[h1],'MarkerColor','b','Subplot',1);
            som_show_add('hit',[h2],'MarkerColor','g','Subplot',1);
            som_show_add('label',sM,'Textsize',8,'TextColor','r','Subplot',1);
            bmusTrain = som_bmus(sM, sD);
            
            sDt=som_read_data(strcat('activity',user_no{index},'01t',char{index2},'.data')); % Test Dataset
            
            if char{index2}=='t'
                sDt=som_normalize(sDt,'log',[1 4 5]);
                sDt=som_normalize(sDt,'logistic',[2 3]);
            end
            if char{index2}=='ta'
                sDt=som_normalize(sDt,'log',[3 4]);
                sDt=som_normalize(sDt,'logistic',[1 2]);
            end
            if char{index2}=='tb'
                sDt=som_normalize(sDt,'log',[1 3 4]);
                sDt=som_normalize(sDt,'logistic',[2]);
            end
            if char{index2}=='tc'
                sDt=som_normalize(sDt,'log',[2 3]);
                sDt=som_normalize(sDt,'logistic',[1]);
            end
            
            htest1=som_hits(sM,sDt);
            
            h3 = som_hits(sM,sDt.data(1:100,:)); % Yes Data in Test Dataset
            h4 = som_hits(sM,sDt.data(101:2000,:)); % No Data in Test Dataset
            
            
            som_show_add('hit',[h3],'MarkerColor','r','Subplot',2);
            som_show_add('hit',[h4],'MarkerColor','y','Subplot',2)
            som_show_add('label',sM,'Textsize',8,'TextColor','b','Subplot',2);
            
            bmusTest = som_bmus(sM, sDt); % To find which instance in which cordinate in map
            
            saveas(gcf,strcat('activity',user_no{index},user_count{index3},char{index2}),'jpg');
            close
        end
    end
end
