#!/bin/bash
MAINCLASS=""
DEPENDS=""
JAVATOCOMPITE=""
OUTPUT=""
while test $# -gt 0; do
        case "$1" in
                -h|--help)
                        echo "compile and create jar file"
                        echo " "
                        echo " [options] path/*.java"
                        echo " "
                        echo "options:"
                        echo "-h, --help                		show brief help"
                        echo "-e package.class,					make exec jar, with main class - package.class"
                        echo "-d \"full_path_to_depends_jars\"  specify a directory to jar format Class-Path"
                        echo "-o filename       				output file name"
                        exit 0
                        ;;
                -e)
                        shift
                        if test $# -gt 0; then
                                export MAINCLASS=$1
                        else
                                echo "non correct args"
                                exit 1
                        fi
                        shift
                        ;;

                -d)
						shift
                        if test $# -gt 0; then
                                export DEPENDS=$1
                        else
                                echo "non correct args"
                                exit 1
                        fi
                        shift
                        ;;
				-o)
						shift
                        if test $# -gt 0; then
                                export OUTPUT=$1
                        else
                                echo "non correct args"
                                exit 1
                        fi
                        shift
                        ;;
                *)
						JAVATOCOMPITE=$1
                        break
                        ;;
        esac
done

touch manifest
echo "Manifest-Version: 1.0" > manifest
if [ -n "$DEPENDS" ]; then
	dep=$(echo $DEPENDS | sed 's/:/ /g')
	echo "Class-Path: $dep" >> manifest
fi
if [ -n "$MAINCLASS" ]; then
	echo "Main-Class: $MAINCLASS" >> manifest
fi
echo "manifest complite"

javac  -cp $DEPENDS:.  $JAVATOCOMPITE
echo "java compile complite"

jar cvmf manifest $OUTPUT.jar ${JAVATOCOMPITE%.*}.class
rm manifest