(() => {
    const forms = [...document.forms].filter(f => f.querySelector('[name="slot"]'));
    const postAll = a => Promise.all(forms.map(f => {
        const fd = new FormData(f); fd.set('action', a);
        return fetch(f.getAttribute('action') || '/', { method: 'POST', body: fd, credentials: 'same-origin' });
    })).then(() => location.reload());
    document.getElementById('startAll').onclick = () => postAll('start');
    document.getElementById('killAll').onclick  = () => postAll('kill');
})();
