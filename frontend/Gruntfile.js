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
            src: 'src/main/javascript',
            src_test: 'src/test/javascript',
            dist: 'build',
            dist_js: '<%= app.dist %>/js',
        },

        clean: {
            dist: ['.tmp',
                '<%= app.dist %>']
        },

        jshint: {
            files: ['Gruntfile.js',
                '<%= app.src %>/*.js',
                '<%= app.src_test %>/*.js']
        },

        concat: {
            options: {
                separator: "\n/*\n * separator\n */\n",
                banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - ' +
                        '<%= grunt.template.today("yyyy-mm-dd") %> */',
            },
            dist: {
                files: {
                    '<%= app.dist_js %>/plugins.js': pluginFiles.minFiles
                }
            },
            dev: {
                options: {
                    sourceMap: true,
                    stripBanners: true,
                },
                files: {
                    '<%= app.dist_js %>/plugins.js': pluginFiles.normalFiles,

                    '<%= app.dist_js %>/app.js': [
                        '<%= app.src %>/**/*.js'
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
                    '<%= app.dist_js %>/app.js': [
                        '<%= app.src %>/**/*.js'
                    ]
                }
            }
        }

    });

    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-autoprefixer');

    grunt.registerTask('build', ['clean', 'jshint', 'concat:dev', 'uglify:dev']);
    grunt.registerTask('dist', ['clean', 'jshint', 'concat:dist', 'uglify:dist']);

    grunt.registerTask('default', ['build']);
};