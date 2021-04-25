/*
 * main.cpp
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#include <boost/program_options.hpp>
#include <iostream>

#include "upside_entry_mainprocess.h"

namespace po = boost::program_options;
using namespace xueqiao::trade::hosting::upside::entry;

int main(int argc, char* argv[]) {
	po::options_description desc("Allowed options");
	desc.add_options()
		   ("help", "produce help message")
		   ("trade", po::value<int64_t>(), "trade account id for upside");

	po::variables_map vm;
	po::store(po::parse_command_line(argc, argv, desc), vm);
	po::notify(vm);

	if(vm.count("help")){
		std::cout<< desc << std::endl;
		return 0;
	}

	if(vm.count("trade")) {
		int64_t trade_account_id= vm["trade"].as<int64_t>();
		if (trade_account_id <= 0) {
			std::cerr << "unexpected trade_account_id=" << trade_account_id << std::endl;
		}

		std::cerr << "startup subprocess for trade_account_id=" << trade_account_id
		          << ", this is for mainprocess main...." << std::endl;
		return -1;
	} else {
		std::cout << "startup mainprocess" << std::endl;
		UpsideEntryMainProcess main_process;
		if (!main_process.runLoop()) {
			return 1;
		}
	}

	return 0;

}
