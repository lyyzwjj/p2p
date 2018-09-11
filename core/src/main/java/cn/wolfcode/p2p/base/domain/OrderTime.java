package cn.wolfcode.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by WangZhe on 2018/7/27.
 */
@Setter
@Getter
public class OrderTime extends BaseDomain{
	private static final long serialVersionUID = 1L;
	private String begin;
    private String end;
}
