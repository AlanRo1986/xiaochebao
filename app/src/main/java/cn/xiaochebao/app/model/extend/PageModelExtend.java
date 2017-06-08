package cn.xiaochebao.app.model.extend;

/**
 * Created by Alan on 2017/04/13 0013.
 */
public class PageModelExtend {

    private int page = 0;
    private int page_total = 0;
    private int page_size = 0;

    public PageModelExtend(){

    }

    public int getPage()
    {
        return page;
    }

    public int getPageSize() {
        return page_size;
    }

    public void setPageSize(int page_size) {
        this.page_size = page_size;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getPageTotal()
    {
        return page_total;
    }

    public void setPageTotal(int page_total)
    {
        this.page_total = page_total;
    }
}
