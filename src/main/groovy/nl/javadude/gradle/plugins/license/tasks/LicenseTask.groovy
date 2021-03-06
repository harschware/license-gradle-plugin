/* License added by: GRADLE-LICENSE-PLUGIN
 *
 * Copyright (C)2011 - Jeroen van Erp <jeroen@javadude.nl>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.javadude.gradle.plugins.license.tasks

import org.gradle.api.tasks.TaskAction

class LicenseTask extends AbstractLicenseTask {

    @TaskAction
    protected void process() {
        licenseLines = loadLicense()
        toBeLicensed = scanForFiles()
        toBeLicensed.findAll({ shouldBeLicensed it }).each { file ->
            licenseFile(file)
        }
    }


    def licenseFile(File file) {
        println "Adding license on " + file
        def license = getLicenseForFile(file)
        def lines = file.readLines()
        file.delete()
        file.createNewFile()
        file.withWriter { w ->
            license.lines.each { line ->
                w.writeLine(line)
            }
            // Add empty line, prevents us from stripping too much in the unlicense task if there is no specific suffix marker
            w.writeLine('')
            lines.each { line ->
                w.writeLine(line)
            }
            w.newLine()
        }
    }
}






