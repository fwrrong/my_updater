package com.fwrrong.my_updater.repository;

import com.fwrrong.my_updater.model.Follow;
import com.fwrrong.my_updater.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UrlRepository  extends JpaRepository<Url, String> {
    boolean findUrlByUrl(String url);
}
