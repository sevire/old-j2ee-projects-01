<style>
  .slicknav_menu {
    display:none;
  }

  @media screen and (max-width: 40em) {
    /* #menu is the original menu */
    #topNav {
      display:none;
    }

    .js .slicknav_menu {
      display:block;
    }
  }
</style>
<div id="header">
   <div id="headerImage">
     <img src="/images/siteimages/header-01.png" />
   </div>
  <div id="mobileTopNav" />
  <div id="topNav">
    <ul id="menu2">
      <li>Parent 1
        <ul>
          <li><a href="#">item 3</a></li>
          <li>Parent 3
            <ul>
              <li><a href="#">item 8</a></li>
              <li><a href="#">item 9</a></li>
              <li><a href="#">item 10</a></li>
            </ul>
          </li>
          <li><a href="#">item 4</a></li>
        </ul>
      </li>
      <li><a href="#">item 1</a></li>
      <li>non-link item</li>
      <li>Parent 2
        <ul>
          <li><a href="#">item 5</a></li>
          <li><a href="#">item 6</a></li>
          <li><a href="#">item 7</a></li>
        </ul>
      </li>
    </ul>
  </div>
  <script>
    $('#menu2').slicknav({
      prependTo:'#mobileTopNav'
    });
  </script>
</div>