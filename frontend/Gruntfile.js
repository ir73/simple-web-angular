module.exports = function(grunt) {

    var pluginFiles = {
        prefixPaths: [
            'bower_components/jquery/dist/jquery',
            'bower_components/angular/angular',
        ],
        minFiles: [],
        normalFiles: []
    };

    for (var i in pluginFiles.prefixPaths) {
        pluginFiles.minFiles.push(pluginFiles.prefixPaths[i] + ".min.js");
        pluginFiles.normalFiles.push(pluginFiles.prefixPaths[i] + ".js");
    }

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        app: {
            src: 'src/main/',
            src_test: 'src/test/',
            dist: 'build',
        },

        clean: {
            dist: ['.tmp',
                '<%= app.dist %>']
        },

        jshint: {
            files: ['Gruntfile.js',
                '<%= app.src %>/javascript/**/*.js',
                '<%= app.src_test %>/javascript/**/*.js']
        },

        concat: {
            options: {
                separator: "\n/*\n * separator\n */\n",
                banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - ' +
                        '<%= grunt.template.today("yyyy-mm-dd") %> */',
            },
            dist: {
                files: {
                    '<%= app.dist %>/static/js/plugins.js': pluginFiles.minFiles
                }
            },
            dev: {
                options: {
                    sourceMap: true,
                    stripBanners: true,
                },
                files: {
                    '<%= app.dist %>/static/js/plugins.js': pluginFiles.normalFiles,

                    '<%= app.dist %>/static/js/app.js': [
                        '<%= app.src %>/javascript/**/*.js'
                    ]
                }
            }
        },


        uglify: {
            dev: {},

            dist: {
                options: {
                    preserveComments: false,
                    sourceMap: true,
                },
                files: {
                    '<%= app.dist %>/static/js/app.js': [
                        '<%= app.src %>/javascript/**/*.js'
                    ]
                }
            }
        },

        copy: {
            dist: {
                files: [{
                    cwd: "<%= app.src %>/assets",
                    expand: true,
                    src: ['**'],
                    dest: '<%= app.dist %>/static'
                }]
            }
        },

        watch: {
            options: {
                spawn: false,
                livereload: true
            },
            copy: {
                files: ["<%= app.src %>/assets/**"],
                tasks: ["copy"],
            },
            scripts: {
                files: ["<%= app.src %>/javascript/**"],
                tasks: ["concat:dev"],
            }
        },

        connect: {
            server: {
                options: {
                    port: 4000,
                    base: "<%= app.dist %>/static",
                    hostname: '*',
                    open: "http://localhost:4000",
                    livereload: false,
                    debug: true
                }
            }
        }

    });

    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-connect');
    grunt.loadNpmTasks('grunt-autoprefixer');

    grunt.registerTask('build', "Builds in debug mode: non-minimized and non-uglified, but concatenated js",
        ['clean', 'jshint', 'copy', 'concat:dev', 'uglify:dev']);
    grunt.registerTask('dist', "Builds in the release mode: minimizes and uglifies js",
        ['clean', 'jshint', 'copy', 'concat:dist', 'uglify:dist']);
    grunt.registerTask('run', "Builds in debug mode, runs local web server on port 9000, starts watching for file modifications",
        ['build', 'connect', 'watch']);

    grunt.registerTask('default', ['build']);
};