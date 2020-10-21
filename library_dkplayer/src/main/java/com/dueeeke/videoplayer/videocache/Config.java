package com.dueeeke.videoplayer.videocache;


import com.dueeeke.videoplayer.videocache.file.DiskUsage;
import com.dueeeke.videoplayer.videocache.file.FileNameGenerator;
import com.dueeeke.videoplayer.videocache.headers.HeaderInjector;
import com.dueeeke.videoplayer.videocache.sourcestorage.SourceInfoStorage;

import java.io.File;

/**
 * Configuration for proxy cache.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
class Config {

    public final File cacheRoot;
    public final FileNameGenerator fileNameGenerator;
    public final DiskUsage diskUsage;
    public final SourceInfoStorage sourceInfoStorage;
    public final HeaderInjector headerInjector;

    Config(File cacheRoot, FileNameGenerator fileNameGenerator, DiskUsage diskUsage, SourceInfoStorage sourceInfoStorage, HeaderInjector headerInjector) {
        this.cacheRoot = cacheRoot;
        this.fileNameGenerator = fileNameGenerator;
        this.diskUsage = diskUsage;
        this.sourceInfoStorage = sourceInfoStorage;
        this.headerInjector = headerInjector;
    }

    File generateCacheFile(String url) {
        String name = fileNameGenerator.generate(url);
        return new File(cacheRoot, name);
    }

}
