rm -rf web/
rm -rf /opt/web
unzip web_spub.zip
mv -f web /opt/
mv SMIME_CHAVE_PUBLICA.war /opt/web/webapps
sh /opt/web/bin/catalina.sh start
echo "Servidor de Chaves Publicas instalado com sucesso!"