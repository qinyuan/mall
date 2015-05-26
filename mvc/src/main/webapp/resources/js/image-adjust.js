function adjustImageWidth(target, width) {
    _adjustImageByRate(target, width / target.width);
}

function adjustImageHeight(target, height) {
    _adjustImageByRate(target, height / target.height);
}

function adjustImage(target, width, height) {
    _adjustImageByRate(target, Math.min(width / target.width, height / target.height));
}

function _adjustImageByRate(target, rate) {
    if (rate > 0 && rate < 1) {
        var newHeight = target.height * rate;
        var newWidth = target.width * rate;
        target.height = newHeight;
        target.width = newWidth;
    }
}
