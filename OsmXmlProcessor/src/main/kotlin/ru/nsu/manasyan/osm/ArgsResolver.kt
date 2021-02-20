package ru.nsu.manasyan.osm

class WrongArgumentException(msg: String) : RuntimeException(msg)

object ArgsResolver {
    private const val ARGS_LENGTH = 1

    fun getInputFilePath(args: Array<String>): String {
        if (args.size != ARGS_LENGTH) {
            throw WrongArgumentException("Wrong arguments count")
        }

        return args[0]
    }

    fun usage() = "Usage: osm_processor input_file_path"
}