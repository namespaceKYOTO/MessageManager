@echo OFF
pushd %JAVA_PATH%
@echo /*----------------------------------------------*/
@echo     java run
@echo /*----------------------------------------------*/
IF %1==toc (
@echo ON
java -classpath %ROOT_PATH%\out\  %2 %3 %4 %5 %6 %7 %8 %9
@echo OFF
) ELSE IF %1==mes (
@echo ON
java -classpath %ROOT_PATH%\out\ MesMan.MesMan %2 %3 %4 %5 %6 %7 %8 %9
@echo OFF
) ELSE IF %1==help (
@echo Enable command
@echo   toc
@echo   mes
@echo e.g run toc
@echo OFF
)
popd
