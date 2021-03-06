module.exports = function(grunt) {

    var appConfig = {
        src: 'src/main',
        src_test: 'src/test',
        dist: 'build',
        tokens: {
            serverEndpoint: grunt.option('endpoint') || "",
            contextRoot: grunt.option('context') || "/",
        }
    };

    /**
     * Replaces all tokens in a format of "@[token]" in the content
     * string
     * @param content   A content (String)
     * @param srcpath   A path to a file (String)
     */
    var replaceTokensFn = function (content, srcpath) {
        var tokenRegex = /\@\[(\w+)\]/g;
        var match;
        while ((match = tokenRegex.exec(content)) !== null) {
            var token = match[0];
            var tokenName = match[1];
            var startPos = match.index;
            var replaceTo = appConfig.tokens[tokenName];

            content = content.substring(0, startPos) +
                replaceTo +
                content.substring(startPos + token.length);

            grunt.log.writeln("Replacing [" + tokenName + "] pos:" +
                startPos + " to '" + replaceTo +
                "' in [" + srcpath + "]");
        }
        return content;
    };

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        app: appConfig,

        clean: {
            all: [
                '.tmp',
                '<%= app.dist %>'
            ]
        },

        jshint: {
            files: ['Gruntfile.js',
                '<%= app.src %>/js/**/*.js',
                '<%= app.src_test %>/js/**/*.js']
        },

        bower_concat: {
            dev: {
                dest: '<%= app.dist %>/static/js/plugins_bower.js',
                cssDest: '<%= app.dist %>/static/css/plugins_bower.css',
                mainFiles: {
                    bootstrap: [ 'dist/css/bootstrap.css',
                        'dist/js/bootstrap.js' ]
                }
            },
            dist: {
                dest: '<%= app.dist %>/static/js/plugins_bower.js',
                cssDest: '<%= app.dist %>/static/css/plugins_bower.css',
                mainFiles: {
                    bootstrap: [ 'dist/css/bootstrap.min.css',
                        'dist/js/bootstrap.min.js' ],
                    "angular-bootstrap": [ 'ui-bootstrap.min.js',
                        'ui-bootstrap-tpls.min.js',
                        'ui-bootstrap-csp.css']
                },
                dependencies: {
                    'bootstrap': 'jquery',
                    'angular': 'jquery',
                    'angular-bootstrap': 'bootstrap',
                }
            }
        },

        copy: {
            options: {
                process: replaceTokensFn
            },
            res: {
                files: [{
                    cwd: "<%= app.src %>",
                    expand: true,
                    src: ['css/**', 'js/**'],
                    dest: '<%= app.dist %>/static'
                }]
            },
            assets: {
                files: [{
                    cwd: "<%= app.src %>/assets",
                    expand: true,
                    src: ['**'],
                    dest: '<%= app.dist %>/static'
                }]
            }
        },

        concat: {
            options: {
                process: replaceTokensFn
            },
        },

        watch: {
            options: {
                spawn: false,
                livereload: true
            },
            copyAssets: {
                files: ["<%= app.src %>/assets/**"],
                tasks: ["copy:assets"],
            },
            jsAndCss: {
                files: ["<%= app.src %>/js/**",
                    "<%= app.src %>/css/**"
                ],
                tasks: ["copy:res"],
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
        },

        useminPrepare: {
            options: {
                dest: '<%= app.dist %>/static',
                root: '<%= app.src %>'
            },
            html: '<%= app.src %>/assets/**/*.*'
        },

        usemin: {
            html: ['<%= app.dist %>/static/**/*.*']
        }

    });

    require('load-grunt-tasks')(grunt);

    grunt.registerTask('dist',
        ['clean',
            'useminPrepare',
            'jshint',
            'copy:assets',
            'bower_concat:dist',
            'concat',
            'uglify',
            'cssmin',
            'usemin']);

    grunt.registerTask('build',
        ['clean',
            'jshint',
            'copy:assets',
            'copy:res',
            'bower_concat:dev']);

    grunt.registerTask('run',
        "Builds in debug mode, runs local web server on port 9000, starts watching for file modifications",
        ['build',
            'connect',
            'watch']);


    grunt.registerTask('default', ['build']);
};