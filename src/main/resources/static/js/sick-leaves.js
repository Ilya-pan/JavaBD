// Переключение между вкладками
function openTab(tabId) {
  document.querySelectorAll('.tab-content').forEach(tab => {
    tab.classList.remove('active');
  });
  document.querySelectorAll('.tab-button').forEach(button => {
    button.classList.remove('active');
  });
  document.getElementById(tabId).classList.add('active');
  event.currentTarget.classList.add('active');
}

// Параметрический запрос - поиск по диагнозу
function searchByDiagnosis() {
  const diagnosis = document.getElementById('diagnosis-search').value;
  if (!diagnosis) {
    alert('Введите диагноз для поиска');
    return;
  }

  fetch(`/sick-leaves/by-diagnosis?diagnosis=${encodeURIComponent(diagnosis)}`)
    .then(response => response.text())
    .then(html => {
      const temp = document.createElement('div');
      temp.innerHTML = html;
      const sickLeaveList = temp.querySelector('.sick-leave-list');
      if (sickLeaveList) {
        document.getElementById('diagnosis-results').innerHTML = sickLeaveList.innerHTML;
      } else {
        document.getElementById('diagnosis-results').innerHTML = '<p>Больничных листов с таким диагнозом не найдено</p>';
      }
    })
    .catch(error => {
      console.error('Error:', error);
      document.getElementById('diagnosis-results').innerHTML = '<p>Ошибка при загрузке данных</p>';
    });
}

// Динамический запрос - фильтрация больничных листов
function applySickLeaveFilters() {
  const filter = {
    patientName: document.getElementById('patientName').value || null,
    doctorName: document.getElementById('doctorName').value || null,
    diagnosis: document.getElementById('diagnosis').value || null,
    status: document.getElementById('status').value || null,
    startDateFrom: document.getElementById('startDateFrom').value || null,
    startDateTo: document.getElementById('startDateTo').value || null
  };

  fetch('/sick-leaves/filter', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(filter)
  })
  .then(response => response.json())
  .then(sickLeaves => {
    updateSickLeaveList(sickLeaves, 'filter-results');
  })
  .catch(error => {
    console.error('Error:', error);
    document.getElementById('filter-results').innerHTML = '<p>Ошибка при загрузке данных</p>';
  });
}

// Обновление списка больничных листов
function updateSickLeaveList(sickLeaves, containerId) {
  const container = document.getElementById(containerId);
  container.innerHTML = '';

  if (!sickLeaves || sickLeaves.length === 0) {
    container.innerHTML = '<p>Больничных листов не найдено</p>';
    return;
  }

  sickLeaves.forEach(sickLeave => {
    const sickLeaveCard = document.createElement('div');
    sickLeaveCard.className = 'sick-leave-card';
    sickLeaveCard.innerHTML = `
      <h3>Пациент: ${sickLeave.patientName}</h3>
      <p>Врач: ${sickLeave.doctorName}</p>
      <p>Диагноз: ${sickLeave.diagnosis}</p>
      <p>Дата начала: ${sickLeave.startDate}</p>
      <p>Дата окончания: ${sickLeave.endDate || 'не указана'}</p>
      <p>Статус: ${sickLeave.status}</p>
    `;
    container.appendChild(sickLeaveCard);
  });
}