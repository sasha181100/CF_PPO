<#-- @ftlvariable name="viewedСomment" type="ru.itmo.wp.domain.Comment" -->

<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <#if viewedPost??>
        <@c.post post=viewedPost/>

        <#if user??>
            <div class="comment-form">
                <form method="post" action="/post/${viewedPost.id}">
                    <div class="field">
                        <div class="comment-header">
                            <label for="text">New comment</label>
                        </div>
                        <div class="comment-text">
                            <textarea id="text" name="text">${viewedСomment.text!}</textarea>
                        </div>
                        <@c.error "viewedСomment.text"/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Write">
                    </div>
                </form>
            </div>
        </#if>

        <#list viewedPost.comments as comment>
            <@c.comment comment=comment/>
        </#list>
    <#else>
        No such post :c
    </#if>
</@c.page>