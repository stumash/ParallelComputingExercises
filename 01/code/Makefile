all: build

.PHONY: clean build

build_folder = build
javac_flags = -d $(build_folder)

base_path = ca/mcgill/ecse420/a1

matrixMult = $(base_path)/matrixMultiplication

din_phil             = $(base_path)/diningPhilosophers
din_phil_w_deadlock  = $(din_phil)/canDeadlock
din_phil_wo_deadlock = $(din_phil)/cannotDeadlock
din_phil_wo_starv    = $(din_phil)/cannotStarve

all_class_files += $(build_folder)/$(matrixMult)/*.class
all_class_files += $(build_folder)/$(din_phil_w_deadlock)/*.class
all_class_files += $(build_folder)/$(din_phil_wo_deadlock)/*.class
all_class_files += $(build_folder)/$(din_phil_wo_starv)/*.class
build: $(all_class_files)

$(build_folder)/$(matrixMult)/*.class: $(matrixMult)/*.java
	javac $(javac_flags) $(matrixMult)/*.java

$(build_folder)/$(din_phil_w_deadlock)/*.class: $(din_phil_w_deadlock)/*.java
	javac $(javac_flags) $(din_phil_w_deadlock)/*.java

$(build_folder)/$(din_phil_wo_deadlock)/*.class: $(din_phil_wo_deadlock)/*.java
	javac $(javac_flags) $(din_phil_wo_deadlock)/*.java

$(build_folder)/$(din_phil_wo_starv)/*.class: $(din_phil_wo_starv)/*.java
	javac $(javac_flags) $(din_phil_wo_starv)/*.java

clean:
	rm -rf $(build_folder) && mkdir $(build_folder)
