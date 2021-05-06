from eclipsegen.preset import Presets

from eclipsegen.generate import EclipseGenerator, EclipseMultiGenerator


class MetaborgEclipseGenerator(object):
  spoofaxRepo = 'http://buildfarm.metaborg.org/job/metaborg/job/spoofax-releng/job/master/lastSuccessfulBuild/artifact/dist/spoofax/eclipse/site/'
  spoofaxRepoLocal = 'spoofax-eclipse/org.metaborg.spoofax.eclipse.updatesite/target/site'
  spoofaxIUs = ['org.metaborg.spoofax.eclipse.feature.feature.group']
  spoofaxLangDevIUs = [
    'org.metaborg.spoofax.eclipse.meta.feature.feature.group',
    'org.metaborg.spoofax.eclipse.meta.m2e.feature.feature.group',
    'de.jcup.yamleditor.feature.group',
  ]
  eclipsePhotonRepo = 'https://artifacts.metaborg.org/content/groups/eclipse-2020-06/'
  yamlEditorRepo = 'https://de-jcup.github.io/update-site-eclipse-yaml-editor/update-site/'

  def __init__(self, workingDir, destination, spoofax=True, spoofaxRepo=None, spoofaxRepoLocal=False,
      langDev=True, lwbDev=True, moreRepos=None, moreIUs=None):
    if spoofaxRepoLocal:
      spoofaxRepo = MetaborgEclipseGenerator.spoofaxRepoLocal
    elif not spoofaxRepo:
      spoofaxRepo = MetaborgEclipseGenerator.spoofaxRepo

    if lwbDev:
      langDev = True

    if not moreRepos:
      moreRepos = []
    if not moreIUs:
      moreIUs = []

    presets = [Presets.platform.value, Presets.java.value, Presets.xml.value, Presets.git.value, Presets.maven.value, Presets.gradle.value]
    if langDev or lwbDev:
      presets.extend([Presets.plugin.value, Presets.plugin_maven.value])
    _, ius = Presets.combine_presets(presets)

    repos = {MetaborgEclipseGenerator.eclipsePhotonRepo}
    repos.add(spoofaxRepo)
    ius.update(MetaborgEclipseGenerator.spoofaxIUs)
    if langDev or lwbDev:
      ius.update(MetaborgEclipseGenerator.spoofaxLangDevIUs)
      repos.add(yamlEditorRepo)

    repos.update(moreRepos)
    ius.update(moreIUs)

    self.workingDir = workingDir
    self.destination = destination
    self.repos = repos
    self.ius = ius

  def generate(self, **kwargs):
    generator = EclipseGenerator(self.workingDir, self.destination, repositories=self.repos, installUnits=self.ius,
      **kwargs)
    return generator.generate()

  def generate_all(self, **kwargs):
    generator = EclipseMultiGenerator(self.workingDir, self.destination, repositories=self.repos, installUnits=self.ius,
      **kwargs)
    return generator.generate()
