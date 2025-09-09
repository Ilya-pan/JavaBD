// Переключение между вкладками
function openTab(tabId) {
  // Скрыть все вкладки
  document.querySelectorAll('.tab-content').forEach(tab => {
    tab.classList.remove('active');
  });

  // Убрать активный класс у всех кнопок
  document.querySelectorAll('.tab-button').forEach(button => {
    button.classList.remove('active');
  });

  // Показать выбранную вкладку
  document.getElementById(tabId).classList.add('active');

  // Сделать кнопку активной
  event.currentTarget.classList.add('active');
}

// Параметрический запрос - поиск по диагнозу
function searchByDiagnosis() {
  const diagnosis = document.getElementById('diagnosis-search').value;
  if (!diagnosis) {
    alert('Введите диагноз для поиска');
    return;
  }

  fetch(`/patients/by-diagnosis?diagnosis=${encodeURIComponent(diagnosis)}`)
    .then(response => response.text())
    .then(html => {
      // Создаем временный элемент для парсинга HTML
      const temp = document.createElement('div');
      temp.innerHTML = html;

      // Извлекаем только список пациентов
      const patientList = temp.querySelector('.patient-list');
      if (patientList) {
        document.getElementById('diagnosis-results').innerHTML = patientList.innerHTML;
      } else {
        document.getElementById('diagnosis-results').innerHTML = '<p>Пациентов с таким диагнозом не найдено</p>';
      }
    })
    .catch(error => {
      console.error('Error:', error);
      document.getElementById('diagnosis-results').innerHTML = '<p>Ошибка при загрузке данных</p>';
    });
}

// Динамический запрос - фильтрация пациентов
function applyPatientFilters() {
  const filter = {
    diagnosis: document.getElementById('diagnosis').value || null,
    minAge: document.getElementById('minAge').value ? parseInt(document.getElementById('minAge').value) : null,
    maxAge: document.getElementById('maxAge').value ? parseInt(document.getElementById('maxAge').value) : null,
    wardType: document.getElementById('wardType').value || null,
    physician: document.getElementById('physician').value || null,
    admissionDateFrom: document.getElementById('admissionDateFrom').value || null,
    admissionDateTo: document.getElementById('admissionDateTo').value || null
  };

  fetch('/patients/filter', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(filter)
  })
  .then(response => response.json())
  .then(patients => {
    updatePatientList(patients, 'filter-results');
  })
  .catch(error => {
    console.error('Error:', error);
    document.getElementById('filter-results').innerHTML = '<p>Ошибка при загрузке данных</p>';
  });
}
// Обновление списка пациентов
function updatePatientList(patients, containerId) {
  const container = document.getElementById(containerId);
  container.innerHTML = '';

  if (!patients || patients.length === 0) {
    container.innerHTML = '<p>Пациентов не найдено</p>';
    return;
  }

  patients.forEach(patient => {
    const patientCard = document.createElement('div');
    patientCard.className = 'patient-card';
    patientCard.innerHTML = `
      <h3>${patient.fullName}</h3>
      <p>Возраст: ${patient.age}</p>
      <p>Диагноз: ${patient.diagnosis}</p>
      <p>Палата: ${patient.wardNumber} (${patient.wardType})</p>
      <p>Лечащий врач: ${patient.attendingPhysician}</p>
      <p>Дата поступления: ${patient.admissionDate}</p>
      <p>Дата выписки: ${patient.dischargeDate || 'не выписан'}</p>
    `;
    container.appendChild(patientCard);
  });
}