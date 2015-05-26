function showBranchGroup($form, index) {
    $form.find('div.branchGroupLetter > div').removeClass('selected')
        .eq(index).addClass('selected');
    $form.find('div.branchGroup').hide().eq(index).show();
}
