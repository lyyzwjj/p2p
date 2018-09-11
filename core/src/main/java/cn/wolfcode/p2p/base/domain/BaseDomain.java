package cn.wolfcode.p2p.base.domain;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by WangZhe on 2018/7/20.
 */
@Setter@Getter
public class BaseDomain implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
}
