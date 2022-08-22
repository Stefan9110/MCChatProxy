package com.github.stefan9110.MCChatProxy.util.configuration;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FileConfiguration {
    @NotNull JSONConfiguration getConfiguration();

    @NotNull String getPath();

    Object getDefault();

    default <T> T getValue() {
        return (T) (getConfiguration().contains(getPath()) ? getConfiguration().get(getPath()) : getDefault());
    }

    default <T> List<T> getList() {
        return (List<T>) getConfiguration().getList(getPath());
    }

    default void setDefault() {
        if (!getConfiguration().contains(getPath())) getConfiguration().set(getPath(), getDefault());
    }
}

