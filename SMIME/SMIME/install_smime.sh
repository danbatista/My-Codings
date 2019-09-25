rm -rf web/
rm -rf /opt/web
unzip web.zip
mv -f web /opt/
mv restful-smime.war /opt/web/webapps
mv USTO_CERT_STORE.war /opt/web/webapps
sh /opt/web/bin/catalina.sh start
echo "SMIME instalado com sucesso!"
