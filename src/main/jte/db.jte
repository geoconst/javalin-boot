@import java.util.List
@import com.yexh.model.Account

@param List<Account> items


@for(Account user : items)
    ${user.getPhone()}<br/>
@endfor