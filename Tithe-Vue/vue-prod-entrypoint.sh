#!/bin/bash

npm run build-prod
rm -r /var/www/html/* && cp -r dist/* /var/www/html/

nginx -g 'daemon off;'
