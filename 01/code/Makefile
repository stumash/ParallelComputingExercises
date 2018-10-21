all: build

.PHONY: clean build

build_folder = build
javac_flags = -d $(build_folder)

base_path = ca/mcgill/ecse420/a1
din_phil          = $(base_path)/diningPhilosophers
din_phil_w_starv  = $(din_phil)/possibleStarvation
din_phil_wo_starv = $(din_phil)/noPossibleStarvation
matrixMult        = $(base_path)/matrixMultiplication

all_class_files += $(build_folder)/$(matrixMult)/*.class
all_class_files += $(build_folder)/$(din_phil_w_starv)/*.class
all_class_files += $(build_folder)/$(din_phil_wo_starv)/*.class
build: $(all_class_files)

$(build_folder)/$(matrixMult)/*.class: $(matrixMult)/*.java
	javac $(javac_flags) $(matrixMult)/*.java

$(build_folder)/$(din_phil_w_starv)/*.class: $(din_phil_w_starv)/*.java
	javac $(javac_flags) $(din_phil_w_starv)/*.java

$(build_folder)/$(din_phil_wo_starv)/*.class: $(din_phil_wo_starv)/*.java
	javac $(javac_flags) $(din_phil_wo_starv)/*.java

clean:
	rm -rf $(build_folder) && mkdir $(build_folder)
